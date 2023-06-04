import DashboardIcon from "@mui/icons-material/Dashboard";
import PersonIcon from '@mui/icons-material/Person';
import MovieCreationIcon from '@mui/icons-material/MovieCreation';
import StorageIcon from '@mui/icons-material/Storage';
import MeetingRoomIcon from '@mui/icons-material/MeetingRoom';
import ReportIcon from '@mui/icons-material/Report';
import ConfirmationNumberIcon from '@mui/icons-material/ConfirmationNumber';
import DiscountIcon from '@mui/icons-material/Discount';


export default [{
        title: "Dashboard",
        icon: DashboardIcon,
        roles: ["ADMIN", "USER_MANAGER", "THEATRE_MANAGER"]
    },
    {
        title: "Users",
        icon: PersonIcon,
        roles: ["ADMIN", "USER_MANAGER"]
    },
    {
        title: "Movies",
        icon: MovieCreationIcon,
        roles: ["ADMIN"]
    },
    {
        title: "Theatres",
        icon: MeetingRoomIcon,
        roles: ["ADMIN", "THEATRE_MANAGER"]
    },
    {
        title: "Tickets",
        icon: ConfirmationNumberIcon,
        roles: ["ADMIN", "THEATRE_MANAGER"]
    },
    {
        title: "Coupons",
        icon: DiscountIcon,
        roles: ["ADMIN", "USER_MANAGER"]
    },
    {
        title: "Reports",
        icon: ReportIcon,
        roles: ["ADMIN", "USER_MANAGER"]
    },

    {
        title: "SQL Queries",
        icon: StorageIcon,
        roles: ["ADMIN"]
    },

];