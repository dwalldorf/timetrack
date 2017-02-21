import {Component, OnChanges, Input, ViewChild} from "@angular/core";
import {GraphData} from "../dashboard/model/graph.data";
import {BaseChartDirective} from "ng2-charts";

@Component({
    selector: 'line-chart',
    templateUrl: '/app/stats/views/stats.html'
})
export class StatsComponent implements OnChanges {

    @Input()
    private data: Array<GraphData>;

    @ViewChild(BaseChartDirective)
    private _chart: any;

    private lineChartData: Array<any>;

    private chartType: string = 'line';

    private lineChartLabels: Array<any>;

    private lineChartOptions: any = {
        responsive: true,
        responsiveAnimationDuration: 500,

        animation: {
            duration: 300,
            easing: 'linear'
        },
        legend: {
            display: false
        },
        elements: {
            line: {
                borderWidth: 3,
                fill: true,
                tension: 0.2
            }
        }
    };

    ngOnChanges() {
        if (this.data) {
            let data: Array<any> = new Array(this.data.length);
            let labels: Array<string> = new Array(this.data.length);

            for (let i = 0; i < this.data.length; i++) {
                data[i] = this.round(this.data[i].duration / 60);
                labels[i] = this.data[i].date;
            }

            this.lineChartData = [{
                data: data,
                pointRadius: 4
            }];
            this.lineChartLabels = labels;

            // labels won't update otherwise..
            if (this._chart) {
                this._chart.chart.config.data.labels = this.lineChartLabels;
            }
        }
    }

    private round(number: number): number {
        let multiplier = Math.pow(10, 2);
        return Math.round(number * multiplier) / multiplier;
    }
}