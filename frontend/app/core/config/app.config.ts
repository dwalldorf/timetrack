export class AppConfig {

    public static ROUTE_HOME: string = '/';
    public static ROUTE_LOGIN: string = '/login';
    public static ROUTE_REGISTER: string = '/register';
    public static ROUTE_SETTINGS: string = '/settings';

    public static ROUTE_WORKLOG: string = '/worklog';
    public static ROUTE_WORKLOG_ADD: string = '/worklog-add';
    public static ROUTE_WORKLOG_EDIT: string = '/worklog-edit/:id';

    public static BACKEND_URI = 'http://localhost:8080';

    public static BACKEND_URI_WORKLOG_SEARCH_CUSTOMERS = AppConfig.BACKEND_URI + '/worklogs/customers?search=';
    public static BACKEND_URI_WORKLOG_SEARCH_PROJECTS = AppConfig.BACKEND_URI + '/worklogs/projects?search=';

    public static getRouterLink(link: string): string {
        return link.substring(1);
    }
}