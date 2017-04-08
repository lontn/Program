/**
 * 
 */


var loadVideoChart = function(result, id) {
    $('#' + id).highcharts({
        title: {
            text: '觀看影片統計',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: TrackingLog',
            x: -20
        },
        xAxis: {
            categories: result.dates
        },
        yAxis: {
            title: {
                text: '總數'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '人'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: result.eventType,
            data: result.sums
        }]
    });
};

var pieChart = function(result, id) {
    $('#' + id).highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Video Interaction Events, 2015'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{name: 'Brands',
                 colorByPoint: true,
                 data:result
        }]
    });
};