export class AppConfig {

    public static readonly ROUTE_HOME: string = '/';
    public static readonly ROUTE_LOGIN: string = '/login';
    public static readonly ROUTE_REGISTER: string = '/register';

    public static getRouterLink(link: string): string {
        return link.substring(1);
    }
}