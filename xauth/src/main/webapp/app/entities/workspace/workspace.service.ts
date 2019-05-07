import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWorkspace } from 'app/shared/model/workspace.model';

type EntityResponseType = HttpResponse<IWorkspace>;
type EntityArrayResponseType = HttpResponse<IWorkspace[]>;

@Injectable({ providedIn: 'root' })
export class WorkspaceService {
    public resourceUrl = SERVER_API_URL + 'api/workspaces';

    constructor(protected http: HttpClient) {}

    create(workspace: IWorkspace): Observable<EntityResponseType> {
        return this.http.post<IWorkspace>(this.resourceUrl, workspace, { observe: 'response' });
    }

    update(workspace: IWorkspace): Observable<EntityResponseType> {
        return this.http.put<IWorkspace>(this.resourceUrl, workspace, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IWorkspace>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWorkspace[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
