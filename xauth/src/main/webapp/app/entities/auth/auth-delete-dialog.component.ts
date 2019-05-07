import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAuth } from 'app/shared/model/auth.model';
import { AuthService } from './auth.service';

@Component({
    selector: 'jhi-auth-delete-dialog',
    templateUrl: './auth-delete-dialog.component.html'
})
export class AuthDeleteDialogComponent {
    auth: IAuth;

    constructor(protected authService: AuthService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.authService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'authListModification',
                content: 'Deleted an auth'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-auth-delete-popup',
    template: ''
})
export class AuthDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ auth }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AuthDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.auth = auth;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/auth', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/auth', { outlets: { popup: null } }]);
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
