import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITemplate } from 'app/shared/model/template.model';

type EntityResponseType = HttpResponse<ITemplate>;
type EntityArrayResponseType = HttpResponse<ITemplate[]>;

@Injectable({ providedIn: 'root' })
export class TemplateService {
    public resourceUrl = SERVER_API_URL + 'api/templates';

    constructor(protected http: HttpClient) {}

    create(template: ITemplate): Observable<EntityResponseType> {
        return this.http.post<ITemplate>(this.resourceUrl, template, { observe: 'response' });
    }

    update(template: ITemplate): Observable<EntityResponseType> {
        return this.http.put<ITemplate>(this.resourceUrl, template, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
