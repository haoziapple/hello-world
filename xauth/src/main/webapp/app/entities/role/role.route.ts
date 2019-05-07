import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Role } from 'app/shared/model/role.model';
import { RoleService } from './role.service';
import { RoleComponent } from './role.component';
import { RoleDetailComponent } from './role-detail.component';
import { RoleUpdateComponent } from './role-update.component';
import { RoleDeletePopupComponent } from './role-delete-dialog.component';
import { IRole } from 'app/shared/model/role.model';

@Injectable({ providedIn: 'root' })
export class RoleResolve implements Resolve<IRole> {
    constructor(private service: RoleService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRole> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Role>) => response.ok),
                map((role: HttpResponse<Role>) => role.body)
            );
        }
        return of(new Role());
    }
}

export const roleRoute: Routes = [
    {
        path: '',
        component: RoleComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'xauthApp.role.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: RoleDetailComponent,
        resolve: {
            role: RoleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.role.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: RoleUpdateComponent,
        resolve: {
            role: RoleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.role.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: RoleUpdateComponent,
        resolve: {
            role: RoleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.role.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const rolePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: RoleDeletePopupComponent,
        resolve: {
            role: RoleResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.role.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
