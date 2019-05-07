import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from './role.service';
import { IAuth } from 'app/shared/model/auth.model';
import { AuthService } from 'app/entities/auth';
import { IMenu } from 'app/shared/model/menu.model';
import { MenuService } from 'app/entities/menu';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile';

@Component({
    selector: 'jhi-role-update',
    templateUrl: './role-update.component.html'
})
export class RoleUpdateComponent implements OnInit {
    role: IRole;
    isSaving: boolean;

    auths: IAuth[];

    menus: IMenu[];

    profiles: IProfile[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected roleService: RoleService,
        protected authService: AuthService,
        protected menuService: MenuService,
        protected profileService: ProfileService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ role }) => {
            this.role = role;
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
        this.profileService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProfile[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProfile[]>) => response.body)
            )
            .subscribe((res: IProfile[]) => (this.profiles = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.role.id !== undefined) {
            this.subscribeToSaveResponse(this.roleService.update(this.role));
        } else {
            this.subscribeToSaveResponse(this.roleService.create(this.role));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRole>>) {
        result.subscribe((res: HttpResponse<IRole>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProfileById(index: number, item: IProfile) {
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
