import { useState, useEffect } from "react";
import { get } from "../../../adapters/xhr";
import { VictoryBar, VictoryChart, VictoryAxis, VictoryTheme, VictoryZoomContainer, VictoryLabel } from 'victory';

const Statistics = ({ advertisement }) => {
  const [weeklyChartData, setWeeklyChartData] = useState([]);
  const [monthlyChartData, setMonthlyChartData] = useState([]);
  const [yearlyhartData, setYearlyChartData] = useState([]);

  useEffect(() => {
    get(`/api/ads/${advertisement.id}/weekly-report`)
    .then((response) => {
      setWeeklyChartData(response.data);
    });
    get(`/api/ads/${advertisement.id}/monthly-report`)
    .then((response) => {
      setMonthlyChartData(response.data);
    });
    get(`/api/ads/${advertisement.id}/yearly-report`)
    .then((response) => {
      setYearlyChartData(response.data);
    });
  }, [])

  return (
    <div>
      <h2>Yearly report</h2>
    <VictoryChart
      // domainPadding will add space to each side of VictoryBar to
      // prevent it from overlapping the axis
        theme={VictoryTheme.material}
      domainPadding={20}
    >
      <VictoryAxis
        // tickValues specifies both the number of ticks and where
        // they are placed on the axis
        tickValues={[0, 1, 2, 3, 4, 5, 6]}
        tickFormat={yearlyhartData.map(x => x.name)}
      />
      <VictoryAxis
        dependentAxis
        // tickFormat specifies how ticks should be displayed
      />
      <VictoryBar
        data={yearlyhartData}
        x="name"
        y="value"
      />
    </VictoryChart>

    <h2>Monthly report</h2>
    <VictoryChart
      // domainPadding will add space to each side of VictoryBar to
      // prevent it from overlapping the axis
        theme={VictoryTheme.material}
      domainPadding={20}
    >
      <VictoryAxis
        // tickValues specifies both the number of ticks and where
        // they are placed on the axis
        tickValues={[0, 1, 2, 3, 4, 5, 6]}
        tickFormat={monthlyChartData.map(x => x.name)}
      />
      <VictoryAxis
        dependentAxis
        // tickFormat specifies how ticks should be displayed
      />
      <VictoryBar
        data={monthlyChartData}
        x="name"
        y="value"
      />
    </VictoryChart>

    <h2>Weekly report</h2>
    <VictoryChart
      // domainPadding will add space to each side of VictoryBar to
      // prevent it from overlapping the axis
        theme={VictoryTheme.material}
      domainPadding={20}
    >
      <VictoryAxis
        // tickValues specifies both the number of ticks and where
        // they are placed on the axis
        tickValues={[0, 1, 2, 3, 4, 5, 6]}
        tickFormat={weeklyChartData.map(x => x.name)}
      />
      <VictoryAxis
        dependentAxis
        // tickFormat specifies how ticks should be displayed
      />
      <VictoryBar
        data={weeklyChartData}
        x="name"
        y="value"
      />
    </VictoryChart>
    </div>
  )
}

export default Statistics;