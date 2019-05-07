/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { XauthTestModule } from '../../../test.module';
import { TemplateUpdateComponent } from 'app/entities/template/template-update.component';
import { TemplateService } from 'app/entities/template/template.service';
import { Template } from 'app/shared/model/template.model';

describe('Component Tests', () => {
    describe('Template Management Update Component', () => {
        let comp: TemplateUpdateComponent;
        let fixture: ComponentFixture<TemplateUpdateComponent>;
        let service: TemplateService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [XauthTestModule],
                declarations: [TemplateUpdateComponent]
            })
                .overrideTemplate(TemplateUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TemplateUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemplateService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Template(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.template = entity;
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
                    const entity = new Template();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.template = entity;
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
