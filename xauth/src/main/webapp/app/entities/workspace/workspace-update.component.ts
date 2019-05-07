import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IWorkspace } from 'app/shared/model/workspace.model';
import { WorkspaceService } from './workspace.service';
import { ISite } from 'app/shared/model/site.model';
import { SiteService } from 'app/entities/site';

@Component({
    selector: 'jhi-workspace-update',
    templateUrl: './workspace-update.component.html'
})
export class WorkspaceUpdateComponent implements OnInit {
    workspace: IWorkspace;
    isSaving: boolean;

    workspaces: IWorkspace[];

    sites: ISite[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected workspaceService: WorkspaceService,
        protected siteService: SiteService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ workspace }) => {
            this.workspace = workspace;
        });
        this.workspaceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IWorkspace[]>) => mayBeOk.ok),
                map((response: HttpResponse<IWorkspace[]>) => response.body)
            )
            .subscribe((res: IWorkspace[]) => (this.workspaces = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.siteService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISite[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISite[]>) => response.body)
            )
            .subscribe((res: ISite[]) => (this.sites = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.workspace.id !== undefined) {
            this.subscribeToSaveResponse(this.workspaceService.update(this.workspace));
        } else {
            this.subscribeToSaveResponse(this.workspaceService.create(this.workspace));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkspace>>) {
        result.subscribe((res: HttpResponse<IWorkspace>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSiteById(index: number, item: ISite) {
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
