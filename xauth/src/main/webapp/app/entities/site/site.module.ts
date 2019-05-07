import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { XauthSharedModule } from 'app/shared';
import {
    SiteComponent,
    SiteDetailComponent,
    SiteUpdateComponent,
    SiteDeletePopupComponent,
    SiteDeleteDialogComponent,
    siteRoute,
    sitePopupRoute
} from './';

const ENTITY_STATES = [...siteRoute, ...sitePopupRoute];

@NgModule({
    imports: [XauthSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SiteComponent, SiteDetailComponent, SiteUpdateComponent, SiteDeleteDialogComponent, SiteDeletePopupComponent],
    entryComponents: [SiteComponent, SiteUpdateComponent, SiteDeleteDialogComponent, SiteDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XauthSiteModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
