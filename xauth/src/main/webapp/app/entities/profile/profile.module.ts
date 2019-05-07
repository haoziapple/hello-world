import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { XauthSharedModule } from 'app/shared';
import {
    ProfileComponent,
    ProfileDetailComponent,
    ProfileUpdateComponent,
    ProfileDeletePopupComponent,
    ProfileDeleteDialogComponent,
    profileRoute,
    profilePopupRoute
} from './';

const ENTITY_STATES = [...profileRoute, ...profilePopupRoute];

@NgModule({
    imports: [XauthSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProfileComponent,
        ProfileDetailComponent,
        ProfileUpdateComponent,
        ProfileDeleteDialogComponent,
        ProfileDeletePopupComponent
    ],
    entryComponents: [ProfileComponent, ProfileUpdateComponent, ProfileDeleteDialogComponent, ProfileDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XauthProfileModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
