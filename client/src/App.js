import './App.css';
import HomePage from './pages/HomePage';
import SearchPage from './pages/SearchPage'
import ProfilePage from './pages/ProfilePage';
import ProfileEditorPage from './pages/ProfileEditorPage';
import RegistrationPage from './pages/RegistrationPage';
import LoginPage from './pages/LoginPage';
import RegistrationConfirmation from './pages/RegistrationConfirmationPage';
import UpdateAdvertisementPage from './pages/UpdateAdvertisementPage';
import RegistrationRequestsPage from './pages/RegistrationRequestsPage';
import RemovalRequestsPage from './pages/RemovalRequestsPage';
import ReportsPage from './pages/ReportsPage';
import ReviewsPage from './pages/ReviewsPage';
import ComplaintsPage from './pages/ComplaintsPage';
import AdminPasswordPage from './pages/AdminPasswordPage';
import RegisterAdminPage from './pages/RegisterAdminPage';
import ManageLoyaltyProgramPage from './pages/ManageLoyaltyProgramPage';
import SetCommissionRatePage from './pages/SetCommissionRatePage';
import ControlPanel from './pages/ControlPanelPage';
import Navbar from './components/util/Navbar';

import NotFound from './pages/NotFound';

import { BrowserRouter as Router, Route, Routes, Head } from 'react-router-dom';

function App() {
  return (
    <Router>
      <div className="App min-h-screen">
        {/* Navbar Component */}
        <Navbar />
        <div className="h-full">
            <Routes>
              <Route path="/" exact element={<HomePage/>}/>
              <Route path="/ads/resorts" exact element={<SearchPage/>}/>
              <Route path="/ads/boats" exact element={<SearchPage/>}/>
              <Route path="/ads/adventures" exact element={<SearchPage/>}/>
              <Route path="/adventure/:id/edit" exact element={<UpdateAdvertisementPage/>}/>
              <Route path="/adventure/:id" exact element={<ProfilePage key={1}/>}/>
              <Route path="/resort/:id" exact element={<ProfilePage key={2}/>}/>
              <Route path="/resort/:id/edit" exact element={<UpdateAdvertisementPage/>}/>
              <Route path="/boat/:id" exact element={<ProfilePage key={3}/>}/>
              <Route path="/boat/:id/edit" exact element={<UpdateAdvertisementPage/>}/>
              <Route path="/advertiser/:id" exact element={<ProfilePage key={4} me={false}/>}/>
              <Route path="/me" exact element={<ProfilePage me={true} key={5}/>}/>
              <Route path="/account" exact element={<ProfileEditorPage/>}/>
              <Route path="/register" exact element={<RegistrationPage/>}/>
              <Route path="/login" exact element={<LoginPage/>}/>
              <Route path="/confirm-registration/:token" exact element={<RegistrationConfirmation/>}/>
              <Route path="/admin/registration-requests" exact element={<RegistrationRequestsPage/>}/>
              <Route path="/admin/removal-requests" exact element={<RemovalRequestsPage/>}/>
              <Route path="/admin/register" exact element={<RegisterAdminPage/>}/>
              <Route path="/admin/system/loyalty" exact element={<ManageLoyaltyProgramPage/>}/>
              <Route path="/admin/system/commission-rate" exact element={<SetCommissionRatePage/>}/>
              <Route path="/admin/setup" exact element={<AdminPasswordPage/>}/>
              <Route path="/admin/reports" exact element={<ReportsPage/>}/>
              <Route path="/admin/reviews" exact element={<ReviewsPage/>}/>
              <Route path="/admin/complaints" exact element={<ComplaintsPage/>}/>
              <Route path="/control-panel" exact element={<ControlPanel/>}/>
              <Route path="*" element={<NotFound/>}/>
            </Routes>
          </div>
      </div>
    </Router>
  );
}

export default App;
