import { ISite } from 'app/shared/model/site.model';

export interface IWorkspace {
    id?: number;
    name?: string;
    remark?: string;
    extmap?: string;
    parentId?: number;
    sites?: ISite[];
}

export class Workspace implements IWorkspace {
    constructor(
        public id?: number,
        public name?: string,
        public remark?: string,
        public extmap?: string,
        public parentId?: number,
        public sites?: ISite[]
    ) {}
}
