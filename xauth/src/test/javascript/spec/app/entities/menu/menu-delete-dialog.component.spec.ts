/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { XauthTestModule } from '../../../test.module';
import { MenuDeleteDialogComponent } from 'app/entities/menu/menu-delete-dialog.component';
import { MenuService } from 'app/entities/menu/menu.service';

describe('Component Tests', () => {
    describe('Menu Management Delete Component', () => {
        let comp: MenuDeleteDialogComponent;
        let fixture: ComponentFixture<MenuDeleteDialogComponent>;
        let service: MenuService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [XauthTestModule],
                declarations: [MenuDeleteDialogComponent]
            })
                .overrideTemplate(MenuDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MenuDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MenuService);
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
