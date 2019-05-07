/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { XauthTestModule } from '../../../test.module';
import { AuthUpdateComponent } from 'app/entities/auth/auth-update.component';
import { AuthService } from 'app/entities/auth/auth.service';
import { Auth } from 'app/shared/model/auth.model';

describe('Component Tests', () => {
    describe('Auth Management Update Component', () => {
        let comp: AuthUpdateComponent;
        let fixture: ComponentFixture<AuthUpdateComponent>;
        let service: AuthService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [XauthTestModule],
                declarations: [AuthUpdateComponent]
            })
                .overrideTemplate(AuthUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AuthUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuthService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Auth(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.auth = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Auth();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.auth = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
