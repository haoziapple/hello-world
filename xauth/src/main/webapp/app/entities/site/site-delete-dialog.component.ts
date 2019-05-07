import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISite } from 'app/shared/model/site.model';
import { SiteService } from './site.service';

@Component({
    selector: 'jhi-site-delete-dialog',
    templateUrl: './site-delete-dialog.component.html'
})
export class SiteDeleteDialogComponent {
    site: ISite;

    constructor(protected siteService: SiteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.siteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'siteListModification',
                content: 'Deleted an site'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-site-delete-popup',
    template: ''
})
export class SiteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ site }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SiteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.site = site;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/site', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/site', { outlets: { popup: null } }]);
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
