import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { XauthSharedModule } from 'app/shared';
import {
    TemplateComponent,
    TemplateDetailComponent,
    TemplateUpdateComponent,
    TemplateDeletePopupComponent,
    TemplateDeleteDialogComponent,
    templateRoute,
    templatePopupRoute
} from './';

const ENTITY_STATES = [...templateRoute, ...templatePopupRoute];

@NgModule({
    imports: [XauthSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TemplateComponent,
        TemplateDetailComponent,
        TemplateUpdateComponent,
        TemplateDeleteDialogComponent,
        TemplateDeletePopupComponent
    ],
    entryComponents: [TemplateComponent, TemplateUpdateComponent, TemplateDeleteDialogComponent, TemplateDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XauthTemplateModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
