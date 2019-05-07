import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { XauthSharedModule } from 'app/shared';
import {
    WorkspaceComponent,
    WorkspaceDetailComponent,
    WorkspaceUpdateComponent,
    WorkspaceDeletePopupComponent,
    WorkspaceDeleteDialogComponent,
    workspaceRoute,
    workspacePopupRoute
} from './';

const ENTITY_STATES = [...workspaceRoute, ...workspacePopupRoute];

@NgModule({
    imports: [XauthSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WorkspaceComponent,
        WorkspaceDetailComponent,
        WorkspaceUpdateComponent,
        WorkspaceDeleteDialogComponent,
        WorkspaceDeletePopupComponent
    ],
    entryComponents: [WorkspaceComponent, WorkspaceUpdateComponent, WorkspaceDeleteDialogComponent, WorkspaceDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XauthWorkspaceModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
