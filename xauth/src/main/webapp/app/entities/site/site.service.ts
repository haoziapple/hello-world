import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISite } from 'app/shared/model/site.model';

type EntityResponseType = HttpResponse<ISite>;
type EntityArrayResponseType = HttpResponse<ISite[]>;

@Injectable({ providedIn: 'root' })
export class SiteService {
    public resourceUrl = SERVER_API_URL + 'api/sites';

    constructor(protected http: HttpClient) {}

    create(site: ISite): Observable<EntityResponseType> {
        return this.http.post<ISite>(this.resourceUrl, site, { observe: 'response' });
    }

    update(site: ISite): Observable<EntityResponseType> {
        return this.http.put<ISite>(this.resourceUrl, site, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISite[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
