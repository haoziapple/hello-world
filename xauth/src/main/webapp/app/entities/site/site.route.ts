import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Site } from 'app/shared/model/site.model';
import { SiteService } from './site.service';
import { SiteComponent } from './site.component';
import { SiteDetailComponent } from './site-detail.component';
import { SiteUpdateComponent } from './site-update.component';
import { SiteDeletePopupComponent } from './site-delete-dialog.component';
import { ISite } from 'app/shared/model/site.model';

@Injectable({ providedIn: 'root' })
export class SiteResolve implements Resolve<ISite> {
    constructor(private service: SiteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISite> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Site>) => response.ok),
                map((site: HttpResponse<Site>) => site.body)
            );
        }
        return of(new Site());
    }
}

export const siteRoute: Routes = [
    {
        path: '',
        component: SiteComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'xauthApp.site.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: SiteDetailComponent,
        resolve: {
            site: SiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.site.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: SiteUpdateComponent,
        resolve: {
            site: SiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.site.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: SiteUpdateComponent,
        resolve: {
            site: SiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.site.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sitePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: SiteDeletePopupComponent,
        resolve: {
            site: SiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.site.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
