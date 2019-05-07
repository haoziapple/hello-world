import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { IWorkspace } from 'app/shared/model/workspace.model';
import { WorkspaceService } from 'app/entities/workspace';
import { IDepartment } from 'app/shared/model/department.model';
import { DepartmentService } from 'app/entities/department';
import { IRole } from 'app/shared/model/role.model';
import { RoleService } from 'app/entities/role';

@Component({
    selector: 'jhi-profile-update',
    templateUrl: './profile-update.component.html'
})
export class ProfileUpdateComponent implements OnInit {
    profile: IProfile;
    isSaving: boolean;

    workspaces: IWorkspace[];

    departments: IDepartment[];

    roles: IRole[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected profileService: ProfileService,
        protected workspaceService: WorkspaceService,
        protected departmentService: DepartmentService,
        protected roleService: RoleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ profile }) => {
            this.profile = profile;
        });
        this.workspaceService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IWorkspace[]>) => mayBeOk.ok),
                map((response: HttpResponse<IWorkspace[]>) => response.body)
            )
            .subscribe((res: IWorkspace[]) => (this.workspaces = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.departmentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDepartment[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepartment[]>) => response.body)
            )
            .subscribe((res: IDepartment[]) => (this.departments = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.roleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRole[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRole[]>) => response.body)
            )
            .subscribe((res: IRole[]) => (this.roles = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.profile.id !== undefined) {
            this.subscribeToSaveResponse(this.profileService.update(this.profile));
        } else {
            this.subscribeToSaveResponse(this.profileService.create(this.profile));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>) {
        result.subscribe((res: HttpResponse<IProfile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDepartmentById(index: number, item: IDepartment) {
        return item.id;
    }

    trackRoleById(index: number, item: IRole) {
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
