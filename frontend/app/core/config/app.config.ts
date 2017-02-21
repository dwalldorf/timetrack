export class AppConfig {

    public static ROUTE_HOME: string = '/';
    public static ROUTE_LOGIN: string = '/login';
    public static ROUTE_REGISTER: string = '/register';

    public static getRouterLink(link: string): string {
        return link.substring(1);
    }
}