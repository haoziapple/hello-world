import { IAuth } from 'app/shared/model/auth.model';
import { IMenu } from 'app/shared/model/menu.model';

export const enum ClientType {
    PC = 'PC',
    APP = 'APP',
    WECHAT = 'WECHAT'
}

export interface ITemplate {
    id?: number;
    spaceId?: number;
    siteId?: number;
    clientType?: ClientType;
    name?: string;
    desc?: string;
    extmap?: string;
    auths?: IAuth[];
    menus?: IMenu[];
}

export class Template implements ITemplate {
    constructor(
        public id?: number,
        public spaceId?: number,
        public siteId?: number,
        public clientType?: ClientType,
        public name?: string,
        public desc?: string,
        public extmap?: string,
        public auths?: IAuth[],
        public menus?: IMenu[]
    ) {}
}
