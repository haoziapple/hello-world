import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITemplate } from 'app/shared/model/template.model';
import { TemplateService } from './template.service';

@Component({
    selector: 'jhi-template-delete-dialog',
    templateUrl: './template-delete-dialog.component.html'
})
export class TemplateDeleteDialogComponent {
    template: ITemplate;

    constructor(protected templateService: TemplateService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.templateService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'templateListModification',
                content: 'Deleted an template'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-template-delete-popup',
    template: ''
})
export class TemplateDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ template }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TemplateDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.template = template;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/template', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/template', { outlets: { popup: null } }]);
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
