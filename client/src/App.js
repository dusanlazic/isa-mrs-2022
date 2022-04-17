import './App.css';
import HomePage from './pages/HomePage'
import ProfilePage from './pages/ProfilePage';
import ProfileEditorPage from './pages/ProfileEditorPage';

import NotFound from './pages/NotFound'

import { BrowserRouter as Router, Route, Routes, Head } from 'react-router-dom'

function App() {
  return (
    <Router>
      <div className="App min-h-screen">
        {/* Navbar Component */}
        <div className="h-full">
            <Routes>
              <Route path="/" exact element={<HomePage/>}/>
              <Route path="/resort/:id" exact element={<ProfilePage/>}/>
              <Route path="/client/:id" exact element={<ProfilePage/>}/>
              <Route path="/boat/:id" exact element={<ProfilePage/>}/>
              <Route path="/edit" exact element={<ProfileEditorPage/>}/>

              <Route path="*" element={<NotFound/>}/>
            </Routes>
          </div>
      </div>
    </Router>
  );
}

export default App;
