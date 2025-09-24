import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom'

function Home() {
  return <div>
    <h2>Carbon Footprint Tracker</h2>
    <p>Track your activities and estimate emissions.</p>
  </div>
}

function Activities() {
  const [items, setItems] = React.useState([])
  const [email, setEmail] = React.useState('admin@demo.io')
  React.useEffect(() => {
    fetch(`http://localhost:8080/api/activities?email=${encodeURIComponent(email)}`)
      .then(r => r.json()).then(setItems).catch(console.error)
  }, [email])
  return <div>
    <h3>Activities</h3>
    <input value={email} onChange={e=>setEmail(e.target.value)} placeholder="email" />
    <ul>
      {items.map(a => <li key={a.id}>{a.category} {a.amount}{a.unit} → {a.emission.toFixed(2)} kgCO2e</li>)}
    </ul>
  </div>
}

function App(){
  return <BrowserRouter>
    <nav style={{display:'flex',gap:12}}>
      <Link to="/">Home</Link>
      <Link to="/activities">Activities</Link>
    </nav>
    <Routes>
      <Route path="/" element={<Home/>} />
      <Route path="/activities" element={<Activities/>} />
    </Routes>
  </BrowserRouter>
}

ReactDOM.createRoot(document.getElementById('root')).render(<App/>)
