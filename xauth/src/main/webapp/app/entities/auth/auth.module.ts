import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { XauthSharedModule } from 'app/shared';
import {
    AuthComponent,
    AuthDetailComponent,
    AuthUpdateComponent,
    AuthDeletePopupComponent,
    AuthDeleteDialogComponent,
    authRoute,
    authPopupRoute
} from './';

const ENTITY_STATES = [...authRoute, ...authPopupRoute];

@NgModule({
    imports: [XauthSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AuthComponent, AuthDetailComponent, AuthUpdateComponent, AuthDeleteDialogComponent, AuthDeletePopupComponent],
    entryComponents: [AuthComponent, AuthUpdateComponent, AuthDeleteDialogComponent, AuthDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XauthAuthModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
