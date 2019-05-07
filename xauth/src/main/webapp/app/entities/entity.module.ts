import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'profile',
                loadChildren: './profile/profile.module#XauthProfileModule'
            },
            {
                path: 'department',
                loadChildren: './department/department.module#XauthDepartmentModule'
            },
            {
                path: 'workspace',
                loadChildren: './workspace/workspace.module#XauthWorkspaceModule'
            },
            {
                path: 'site',
                loadChildren: './site/site.module#XauthSiteModule'
            },
            {
                path: 'role',
                loadChildren: './role/role.module#XauthRoleModule'
            },
            {
                path: 'menu',
                loadChildren: './menu/menu.module#XauthMenuModule'
            },
            {
                path: 'auth',
                loadChildren: './auth/auth.module#XauthAuthModule'
            },
            {
                path: 'template',
                loadChildren: './template/template.module#XauthTemplateModule'
            },
            {
                path: 'profile',
                loadChildren: './profile/profile.module#XauthProfileModule'
            },
            {
                path: 'department',
                loadChildren: './department/department.module#XauthDepartmentModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class XauthEntityModule {}
