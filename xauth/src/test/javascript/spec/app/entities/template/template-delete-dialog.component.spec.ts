/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { XauthTestModule } from '../../../test.module';
import { TemplateDeleteDialogComponent } from 'app/entities/template/template-delete-dialog.component';
import { TemplateService } from 'app/entities/template/template.service';

describe('Component Tests', () => {
    describe('Template Management Delete Component', () => {
        let comp: TemplateDeleteDialogComponent;
        let fixture: ComponentFixture<TemplateDeleteDialogComponent>;
        let service: TemplateService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [XauthTestModule],
                declarations: [TemplateDeleteDialogComponent]
            })
                .overrideTemplate(TemplateDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TemplateDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemplateService);
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
