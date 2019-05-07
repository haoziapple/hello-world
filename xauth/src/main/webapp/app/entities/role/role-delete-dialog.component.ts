import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRole } from 'app/shared/model/role.model';
import { RoleService } from './role.service';

@Component({
    selector: 'jhi-role-delete-dialog',
    templateUrl: './role-delete-dialog.component.html'
})
export class RoleDeleteDialogComponent {
    role: IRole;

    constructor(protected roleService: RoleService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.roleService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'roleListModification',
                content: 'Deleted an role'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-role-delete-popup',
    template: ''
})
export class RoleDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ role }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RoleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.role = role;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/role', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/role', { outlets: { popup: null } }]);
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
