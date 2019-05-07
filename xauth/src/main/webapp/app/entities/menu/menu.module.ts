import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { XauthSharedModule } from 'app/shared';
import {
    MenuComponent,
    MenuDetailComponent,
    MenuUpdateComponent,
    MenuDeletePopupComponent,
    MenuDeleteDialogComponent,
    menuRoute,
    menuPopupRoute
} from './';

const ENTITY_STATES = [...menuRoute, ...menuPopupRoute];

@NgModule({
    imports: [XauthSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [MenuComponent, MenuDetailComponent, MenuUpdateComponent, MenuDeleteDialogComponent, MenuDeletePopupComponent],
    entryComponents: [MenuComponent, MenuUpdateComponent, MenuDeleteDialogComponent, MenuDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XauthMenuModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
