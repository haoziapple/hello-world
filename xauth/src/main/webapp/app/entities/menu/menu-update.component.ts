import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from './menu.service';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';
import { ITemplate } from 'app/shared/model/template.model';
import { TemplateService } from 'app/entities/template';
import { IAuth } from 'app/shared/model/auth.model';
import { AuthService } from 'app/entities/auth';

@Component({
    selector: 'jhi-menu-update',
    templateUrl: './menu-update.component.html'
})
export class MenuUpdateComponent implements OnInit {
    menu: IMenu;
    isSaving: boolean;

    menus: IMenu[];

    roles: IRole[];

    templates: ITemplate[];

    auths: IAuth[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected menuService: MenuService,
        protected roleService: RoleService,
        protected templateService: TemplateService,
        protected authService: AuthService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ menu }) => {
            this.menu = menu;
        });
        this.menuService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMenu[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMenu[]>) => response.body)
            )
            .subscribe((res: IMenu[]) => (this.menus = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        this.authService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAuth[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAuth[]>) => response.body)
            )
            .subscribe((res: IAuth[]) => (this.auths = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.menu.id !== undefined) {
            this.subscribeToSaveResponse(this.menuService.update(this.menu));
        } else {
            this.subscribeToSaveResponse(this.menuService.create(this.menu));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMenu>>) {
        result.subscribe((res: HttpResponse<IMenu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMenuById(index: number, item: IMenu) {
        return item.id;
    }

    trackRoleById(index: number, item: IRole) {
        return item.id;
    }

    trackTemplateById(index: number, item: ITemplate) {
        return item.id;
    }

    trackAuthById(index: number, item: IAuth) {
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
