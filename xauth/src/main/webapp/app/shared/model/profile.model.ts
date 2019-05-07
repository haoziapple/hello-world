import { IDepartment } from 'app/shared/model/department.model';
import { IRole } from 'app/shared/model/role.model';

export const enum Sex {
    MALE = 'MALE',
    FEMALE = 'FEMALE'
}

export interface IProfile {
    id?: number;
    name?: string;
    loginName?: string;
    password?: string;
    mobile?: string;
    email?: string;
    sex?: Sex;
    locked?: boolean;
    remark?: string;
    extMap?: string;
    workspaceId?: number;
    departments?: IDepartment[];
    roles?: IRole[];
}

export class Profile implements IProfile {
    constructor(
        public id?: number,
        public name?: string,
        public loginName?: string,
        public password?: string,
        public mobile?: string,
        public email?: string,
        public sex?: Sex,
        public locked?: boolean,
        public remark?: string,
        public extMap?: string,
        public workspaceId?: number,
        public departments?: IDepartment[],
        public roles?: IRole[]
    ) {
        this.locked = this.locked || false;
    }
}
