import { IRole } from 'app/shared/model/role.model';
import { ITemplate } from 'app/shared/model/template.model';
import { IAuth } from 'app/shared/model/auth.model';

export interface IMenu {
    id?: number;
    name?: string;
    code?: string;
    seq?: number;
    state?: string;
    url?: string;
    leaf?: boolean;
    showFlag?: boolean;
    extmap?: string;
    parentId?: number;
    roles?: IRole[];
    templates?: ITemplate[];
    auths?: IAuth[];
}

export class Menu implements IMenu {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public seq?: number,
        public state?: string,
        public url?: string,
        public leaf?: boolean,
        public showFlag?: boolean,
        public extmap?: string,
        public parentId?: number,
        public roles?: IRole[],
        public templates?: ITemplate[],
        public auths?: IAuth[]
    ) {
        this.leaf = this.leaf || false;
        this.showFlag = this.showFlag || false;
    }
}
