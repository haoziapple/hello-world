import { IRole } from 'app/shared/model/role.model';
import { ITemplate } from 'app/shared/model/template.model';
import { IMenu } from 'app/shared/model/menu.model';

export interface IAuth {
    id?: number;
    name?: string;
    code?: string;
    desc?: string;
    extmap?: string;
    roles?: IRole[];
    templates?: ITemplate[];
    menus?: IMenu[];
}

export class Auth implements IAuth {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public desc?: string,
        public extmap?: string,
        public roles?: IRole[],
        public templates?: ITemplate[],
        public menus?: IMenu[]
    ) {}
}
