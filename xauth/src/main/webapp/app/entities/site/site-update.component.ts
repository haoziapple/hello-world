import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISite } from 'app/shared/model/site.model';
import { SiteService } from './site.service';
import { IWorkspace } from 'app/shared/model/workspace.model';
import { WorkspaceService } from 'app/entities/workspace';

@Component({
    selector: 'jhi-site-update',
    templateUrl: './site-update.component.html'
})
export class SiteUpdateComponent implements OnInit {
    site: ISite;
    isSaving: boolean;

    workspaces: IWorkspace[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected siteService: SiteService,
        protected workspaceService: WorkspaceService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ site }) => {
            this.site = site;
        });
        this.workspaceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IWorkspace[]>) => mayBeOk.ok),
                map((response: HttpResponse<IWorkspace[]>) => response.body)
            )
            .subscribe((res: IWorkspace[]) => (this.workspaces = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.site.id !== undefined) {
            this.subscribeToSaveResponse(this.siteService.update(this.site));
        } else {
            this.subscribeToSaveResponse(this.siteService.create(this.site));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISite>>) {
        result.subscribe((res: HttpResponse<ISite>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackWorkspaceById(index: number, item: IWorkspace) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
