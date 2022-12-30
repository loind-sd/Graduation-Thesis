import React, { Component } from "react";
import ReactApexChart from "apexcharts";
class ChartUser extends Component {
  
  render() {
    return (
      <div id="chart">
        <ReactApexChart
          options={this.state.options}
          series={this.state.series}
          type="line"
          height={350}
        />
      </div>
    );
  }
}
function ApexChartUser() {
  return <ChartUser />;
}
export default ApexChartUser;
