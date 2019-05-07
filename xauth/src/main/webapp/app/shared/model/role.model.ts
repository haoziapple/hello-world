import { IAuth } from 'app/shared/model/auth.model';
import { IMenu } from 'app/shared/model/menu.model';
import { IProfile } from 'app/shared/model/profile.model';

export const enum ClientType {
    PC = 'PC',
    APP = 'APP',
    WECHAT = 'WECHAT'
}

export const enum RoleType {
    SYSTEM_ADMIN = 'SYSTEM_ADMIN',
    ADMIN = 'ADMIN',
    USER = 'USER'
}

export interface IRole {
    id?: number;
    spaceId?: number;
    siteId?: number;
    clientType?: ClientType;
    name?: string;
    remark?: string;
    extmap?: string;
    roleType?: RoleType;
    auths?: IAuth[];
    menus?: IMenu[];
    profiles?: IProfile[];
}

export class Role implements IRole {
    constructor(
        public id?: number,
        public spaceId?: number,
        public siteId?: number,
        public clientType?: ClientType,
        public name?: string,
        public remark?: string,
        public extmap?: string,
        public roleType?: RoleType,
        public auths?: IAuth[],
        public menus?: IMenu[],
        public profiles?: IProfile[]
    ) {}
}
