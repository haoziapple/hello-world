import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from './menu.service';

@Component({
    selector: 'jhi-menu-delete-dialog',
    templateUrl: './menu-delete-dialog.component.html'
})
export class MenuDeleteDialogComponent {
    menu: IMenu;

    constructor(protected menuService: MenuService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.menuService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'menuListModification',
                content: 'Deleted an menu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-menu-delete-popup',
    template: ''
})
export class MenuDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ menu }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MenuDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.menu = menu;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/menu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/menu', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
