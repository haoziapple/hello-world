import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Auth } from 'app/shared/model/auth.model';
import { AuthService } from './auth.service';
import { AuthComponent } from './auth.component';
import { AuthDetailComponent } from './auth-detail.component';
import { AuthUpdateComponent } from './auth-update.component';
import { AuthDeletePopupComponent } from './auth-delete-dialog.component';
import { IAuth } from 'app/shared/model/auth.model';

@Injectable({ providedIn: 'root' })
export class AuthResolve implements Resolve<IAuth> {
    constructor(private service: AuthService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAuth> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Auth>) => response.ok),
                map((auth: HttpResponse<Auth>) => auth.body)
            );
        }
        return of(new Auth());
    }
}

export const authRoute: Routes = [
    {
        path: '',
        component: AuthComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'xauthApp.auth.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: AuthDetailComponent,
        resolve: {
            auth: AuthResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.auth.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: AuthUpdateComponent,
        resolve: {
            auth: AuthResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.auth.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: AuthUpdateComponent,
        resolve: {
            auth: AuthResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.auth.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const authPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: AuthDeletePopupComponent,
        resolve: {
            auth: AuthResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.auth.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
