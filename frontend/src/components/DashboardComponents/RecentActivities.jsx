import React, { useEffect, useState } from 'react';

import { Chip, Pagination, PaginationItem, Paper, Table, TableBody, TableContainer, TableHead, TableRow } from '@mui/material';

import CustomTableCell from './CustomTableCell';
import { getActivities, getActivityCount } from '../../api/logs';
import { getISODateTime } from '../../utils/date';
import { getColor } from '../../utils/object';

const ACTIVITY_PER_PAGE = 10;

const RecentActivities = () => {
  const [activities, setActivities] = useState([]);
  const [page, setPage] = useState(1);
  const [count, setCount] = useState(0);

  useEffect(() => {
    const fetchActivities = async () => {
      const res = await getActivities(0, { page, size: ACTIVITY_PER_PAGE });
      setActivities(res);
      setCount(await getActivityCount());
    };
    fetchActivities();

    return () => {
      setActivities([]);
      setCount(0);
    };
  }, [page]);

  return (
    <TableContainer
      component={Paper}
      sx={{
        borderRadius: 2,
        backgroundColor: '#051d32',
        maxHeight: window.innerHeight * 0.6,
        overflow: 'scroll',
      }}
    >
      <Table sx={{ minWidth: 700 }} stickyHeader>
        <TableHead>
          <TableRow>
            <CustomTableCell>
              <b> Type </b>
            </CustomTableCell>
            <CustomTableCell>
              <b> User </b>
            </CustomTableCell>
            <CustomTableCell>
              <b> Activity </b>
            </CustomTableCell>
            <CustomTableCell>
              <b> Time </b>
            </CustomTableCell>
          </TableRow>
        </TableHead>
        <TableBody sx={{ overflow: 'scroll', maxHeight: '400px' }}>
          {activities.map((activity) => (
            <TableRow key={activity.activityId}>
              <CustomTableCell>
                <Chip label={activity.activityType} sx={{ backgroundColor: getColor(activity.activityType), color: '#fff', borderRadius: '5px' , width: '100px'}} />
              </CustomTableCell>
              <CustomTableCell>{activity.username}</CustomTableCell>
              <CustomTableCell>{activity.activityBody}</CustomTableCell>
              <CustomTableCell>{getISODateTime(activity.issueTimestamp)}</CustomTableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Pagination  color="primary" count={Math.floor(count / ACTIVITY_PER_PAGE)} page={page} onChange={(e, value) => setPage(value)} renderItem={(item) => <PaginationItem {...item} sx={{ color: '#fff' }} />} />

    </TableContainer>
  );
};

export default RecentActivities;
