import { IProfile } from 'app/shared/model/profile.model';

export interface IDepartment {
    id?: number;
    name?: string;
    code?: string;
    seq?: number;
    leaf?: boolean;
    remark?: string;
    extMap?: string;
    workspaceId?: number;
    parentId?: number;
    profiles?: IProfile[];
}

export class Department implements IDepartment {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public seq?: number,
        public leaf?: boolean,
        public remark?: string,
        public extMap?: string,
        public workspaceId?: number,
        public parentId?: number,
        public profiles?: IProfile[]
    ) {
        this.leaf = this.leaf || false;
    }
}
