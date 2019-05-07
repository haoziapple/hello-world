/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { XauthTestModule } from '../../../test.module';
import { WorkspaceDeleteDialogComponent } from 'app/entities/workspace/workspace-delete-dialog.component';
import { WorkspaceService } from 'app/entities/workspace/workspace.service';

describe('Component Tests', () => {
    describe('Workspace Management Delete Component', () => {
        let comp: WorkspaceDeleteDialogComponent;
        let fixture: ComponentFixture<WorkspaceDeleteDialogComponent>;
        let service: WorkspaceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [XauthTestModule],
                declarations: [WorkspaceDeleteDialogComponent]
            })
                .overrideTemplate(WorkspaceDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WorkspaceDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkspaceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
