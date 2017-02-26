export class AppConfig {

    public static ROUTE_HOME: string = '/';
    public static ROUTE_LOGIN: string = '/login';
    public static ROUTE_REGISTER: string = '/register';
    public static ROUTE_SETTINGS: string = '/settings';
    public static ROUTE_WORKLOG_ADD: string = '/worklog-add';

    public static getRouterLink(link: string): string {
        return link.substring(1);
    }
}