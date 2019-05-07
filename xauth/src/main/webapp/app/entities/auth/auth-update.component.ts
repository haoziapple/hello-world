import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAuth } from 'app/shared/model/auth.model';
import { AuthService } from './auth.service';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';
import { ITemplate } from 'app/shared/model/template.model';
import { TemplateService } from 'app/entities/template';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from 'app/entities/menu';

@Component({
    selector: 'jhi-auth-update',
    templateUrl: './auth-update.component.html'
})
export class AuthUpdateComponent implements OnInit {
    auth: IAuth;
    isSaving: boolean;

    roles: IRole[];

    templates: ITemplate[];

    menus: IMenu[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected authService: AuthService,
        protected roleService: RoleService,
        protected templateService: TemplateService,
        protected menuService: MenuService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ auth }) => {
            this.auth = auth;
        });
        this.roleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRole[]>) => response.body)
            )
            .subscribe((res: IRole[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.templateService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITemplate[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITemplate[]>) => response.body)
            )
            .subscribe((res: ITemplate[]) => (this.templates = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.auth.id !== undefined) {
            this.subscribeToSaveResponse(this.authService.update(this.auth));
        } else {
            this.subscribeToSaveResponse(this.authService.create(this.auth));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuth>>) {
        result.subscribe((res: HttpResponse<IAuth>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRoleById(index: number, item: IRole) {
        return item.id;
    }

    trackTemplateById(index: number, item: ITemplate) {
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
