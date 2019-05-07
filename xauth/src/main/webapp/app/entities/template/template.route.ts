import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Template } from 'app/shared/model/template.model';
import { TemplateService } from './template.service';
import { TemplateComponent } from './template.component';
import { TemplateDetailComponent } from './template-detail.component';
import { TemplateUpdateComponent } from './template-update.component';
import { TemplateDeletePopupComponent } from './template-delete-dialog.component';
import { ITemplate } from 'app/shared/model/template.model';

@Injectable({ providedIn: 'root' })
export class TemplateResolve implements Resolve<ITemplate> {
    constructor(private service: TemplateService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITemplate> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Template>) => response.ok),
                map((template: HttpResponse<Template>) => template.body)
            );
        }
        return of(new Template());
    }
}

export const templateRoute: Routes = [
    {
        path: '',
        component: TemplateComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'xauthApp.template.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TemplateDetailComponent,
        resolve: {
            template: TemplateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.template.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TemplateUpdateComponent,
        resolve: {
            template: TemplateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.template.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TemplateUpdateComponent,
        resolve: {
            template: TemplateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.template.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const templatePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TemplateDeletePopupComponent,
        resolve: {
            template: TemplateResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'xauthApp.template.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
