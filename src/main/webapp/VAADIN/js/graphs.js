window.onresize = function() {
    if(this.resizeTO) clearTimeout(this.resizeTO);
    this.resizeTO = setTimeout(function() {
        document.dispatchEvent(new CustomEvent('resizeEnd'));
    }, 200);
};
var graph = {
    createPieChart : function  ( values, settings ) {
        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback( function() { graph.drawPieChart( values, settings ); });
    },
    drawPieChart : function ( values, settings)  {
        console.log(settings);
        var data = google.visualization.arrayToDataTable( values );

        var options = {
            title: settings.title
        };
        var chart = new google.visualization.PieChart( document.getElementById( settings.container ) );
        chart.draw(data, options);

        document.addEventListener('resizeEnd', function(customEvent) {
            graph.drawPieChart( values, settings );
        });
    },

    createLineChart : function ( values, settings ) {
        google.charts.load('current', {'packages': ['corechart']});
        google.charts.setOnLoadCallback( function () { graph.drawLineChart( values, settings ); });
    },

    drawLineChart : function ( values, settings ) {
        console.log(settings);
        var data = new google.visualization.DataTable();
        data.addColumn('number', 'X');
        for( var i = 0; i < settings.columns.length ; i++  ) {
            data.addColumn('number', settings.columns[i]);
        }

        data.addRows( values );

        var options = {
            hAxis: {
                title: settings.haxis
            },
            vAxis: {
                title: settings.vaxis
            },
            series: {
                1: {curveType: 'function'}
            }
        };
        var chart = new google.visualization.LineChart( document.getElementById( settings.container ));
        chart.draw(data, options);

        document.addEventListener('resizeEnd', function(customEvent) {
            graph.drawLineChart( values, settings );
        });
    },
    createBarChart : function ( data, container, options ) {

    }
}