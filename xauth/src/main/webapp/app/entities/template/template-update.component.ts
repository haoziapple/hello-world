import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITemplate } from 'app/shared/model/template.model';
import { TemplateService } from './template.service';
import { IAuth } from 'app/shared/model/auth.model';
import { AuthService } from 'app/entities/auth';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from 'app/entities/menu';

@Component({
    selector: 'jhi-template-update',
    templateUrl: './template-update.component.html'
})
export class TemplateUpdateComponent implements OnInit {
    template: ITemplate;
    isSaving: boolean;

    auths: IAuth[];

    menus: IMenu[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected templateService: TemplateService,
        protected authService: AuthService,
        protected menuService: MenuService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ template }) => {
            this.template = template;
        });
        this.authService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAuth[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAuth[]>) => response.body)
            )
            .subscribe((res: IAuth[]) => (this.auths = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.menuService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMenu[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMenu[]>) => response.body)
            )
            .subscribe((res: IMenu[]) => (this.menus = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.template.id !== undefined) {
            this.subscribeToSaveResponse(this.templateService.update(this.template));
        } else {
            this.subscribeToSaveResponse(this.templateService.create(this.template));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITemplate>>) {
        result.subscribe((res: HttpResponse<ITemplate>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAuthById(index: number, item: IAuth) {
        return item.id;
    }

    trackMenuById(index: number, item: IMenu) {
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
