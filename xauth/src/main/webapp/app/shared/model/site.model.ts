import { IWorkspace } from 'app/shared/model/workspace.model';

export interface ISite {
    id?: number;
    name?: string;
    homeUrl?: string;
    remark?: string;
    extmap?: string;
    workspaces?: IWorkspace[];
}

export class Site implements ISite {
    constructor(
        public id?: number,
        public name?: string,
        public homeUrl?: string,
        public remark?: string,
        public extmap?: string,
        public workspaces?: IWorkspace[]
    ) {}
}
