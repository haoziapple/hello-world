/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { XauthTestModule } from '../../../test.module';
import { WorkspaceUpdateComponent } from 'app/entities/workspace/workspace-update.component';
import { WorkspaceService } from 'app/entities/workspace/workspace.service';
import { Workspace } from 'app/shared/model/workspace.model';

describe('Component Tests', () => {
    describe('Workspace Management Update Component', () => {
        let comp: WorkspaceUpdateComponent;
        let fixture: ComponentFixture<WorkspaceUpdateComponent>;
        let service: WorkspaceService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [XauthTestModule],
                declarations: [WorkspaceUpdateComponent]
            })
                .overrideTemplate(WorkspaceUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WorkspaceUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkspaceService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Workspace(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.workspace = entity;
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
                    const entity = new Workspace();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.workspace = entity;
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
