import React, { useState, useEffect } from 'react';
import { ChevronDownIcon, PlayIcon } from '@heroicons/react/24/outline';

const SpeedyServeLanding = () => {
  const [isVisible, setIsVisible] = useState(false);

  useEffect(() => {
    setIsVisible(true);
  }, []);

  return (
    <div className="min-h-screen bg-white font-inter">
      {/* Navigation */}
      <nav className="fixed top-0 w-full bg-white/90 backdrop-blur-md z-50 border-b border-gray-100">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <div className="flex items-center">
              <div className="bg-orange-500 rounded-lg p-2">
                <span className="text-white font-bold text-xl">SS</span>
              </div>
              <span className="ml-3 text-xl font-bold text-gray-900">SpeedyServe</span>
            </div>
            <div className="hidden md:flex space-x-8">
              <a href="#features" className="text-gray-600 hover:text-orange-500 transition-colors">Features</a>
              <a href="#how-it-works" className="text-gray-600 hover:text-orange-500 transition-colors">How It Works</a>
              <a href="#canteen" className="text-gray-600 hover:text-orange-500 transition-colors">For Canteens</a>
              <a href="#download" className="text-gray-600 hover:text-orange-500 transition-colors">Download</a>
            </div>
            <button className="bg-orange-500 text-white px-6 py-2 rounded-full hover:bg-orange-600 transition-all duration-300 hover:scale-105">
              Login
            </button>
          </div>
        </div>
      </nav>

      {/* Hero Section */}
      <section className="pt-24 pb-16 px-4 sm:px-6 lg:px-8">
        <div className="max-w-7xl mx-auto">
          <div className="grid lg:grid-cols-2 gap-12 items-center">
            <div className={`space-y-8 ${isVisible ? 'animate-fade-in' : 'opacity-0'}`}>
              <div className="space-y-6">
                <h1 className="text-5xl lg:text-6xl font-bold text-gray-900 leading-tight">
                  Get Your Meals
                  <span className="text-orange-500"> Faster</span>,
                  <span className="text-orange-500"> Smarter</span>,
                  <span className="text-orange-500"> Easier</span>
                </h1>
                <p className="text-xl text-gray-600 leading-relaxed">
                  Skip the queues, pre-book your meals, and get real-time updates. 
                  The smartest way to order food in your college canteen.
                </p>
              </div>
              <div className="flex flex-col sm:flex-row gap-4">
                <button className="bg-orange-500 text-white px-8 py-4 rounded-full font-semibold hover:bg-orange-600 transition-all duration-300 hover:scale-105 shadow-lg hover:shadow-xl">
                  Order Now
                </button>
                <button className="border-2 border-orange-500 text-orange-500 px-8 py-4 rounded-full font-semibold hover:bg-orange-50 transition-all duration-300 hover:scale-105">
                  Download App
                </button>
              </div>
            </div>
            <div className={`relative ${isVisible ? 'animate-fade-in-delay' : 'opacity-0'}`}>
              <div className="bg-gradient-to-r from-orange-400 to-orange-600 rounded-3xl p-8 shadow-2xl">
                <div className="bg-white rounded-2xl p-6 shadow-lg">
                  <div className="space-y-4">
                    <div className="flex items-center space-x-3">
                      <div className="w-12 h-12 bg-orange-100 rounded-full flex items-center justify-center">
                        <span className="text-orange-500 text-xl">🍔</span>
                      </div>
                      <div>
                        <h3 className="font-semibold">Burger Combo</h3>
                        <p className="text-gray-500">₹120</p>
                      </div>
                      <button className="ml-auto bg-orange-500 text-white px-4 py-2 rounded-full text-sm">
                        Add
                      </button>
                    </div>
                    <div className="border-t pt-4">
                      <div className="flex justify-between items-center">
                        <span className="text-gray-600">Pickup Time</span>
                        <select className="border border-gray-200 rounded-lg px-3 py-1">
                          <option>12:30 PM</option>
                          <option>1:00 PM</option>
                          <option>1:30 PM</option>
                        </select>
                      </div>
                    </div>
                    <button className="w-full bg-orange-500 text-white py-3 rounded-xl font-semibold">
                      Order Now
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* How It Works */}
      <section id="how-it-works" className="py-20 bg-gray-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold text-gray-900 mb-4">How It Works</h2>
            <p className="text-xl text-gray-600">Three simple steps to get your food faster</p>
          </div>
          <div className="grid md:grid-cols-3 gap-8">
            {[
              {
                step: "01",
                title: "Browse & Order",
                description: "Browse menu items and add your favorites to cart",
                icon: "📱"
              },
              {
                step: "02",
                title: "Choose Time Slot",
                description: "Select your preferred pickup time to avoid queues",
                icon: "⏰"
              },
              {
                step: "03",
                title: "Get Notified",
                description: "Receive real-time updates when your food is ready",
                icon: "🔔"
              }
            ].map((item, index) => (
              <div key={index} className="text-center group">
                <div className="bg-white rounded-2xl p-8 shadow-lg hover:shadow-xl transition-all duration-300 hover:-translate-y-2">
                  <div className="text-6xl mb-4">{item.icon}</div>
                  <div className="text-orange-500 font-bold text-sm mb-2">STEP {item.step}</div>
                  <h3 className="text-xl font-bold text-gray-900 mb-4">{item.title}</h3>
                  <p className="text-gray-600">{item.description}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Features for Students */}
      <section id="features" className="py-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold text-gray-900 mb-4">Core Features for Students</h2>
            <p className="text-xl text-gray-600">Everything you need for a seamless food ordering experience</p>
          </div>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
            {[
              {
                icon: "📌",
                title: "Pre-book Meals",
                description: "Avoid long queues by pre-booking your meals"
              },
              {
                icon: "📊",
                title: "Real-time Updates",
                description: "Get live updates on your order status"
              },
              {
                icon: "⏱️",
                title: "Flexible Time Slots",
                description: "Choose pickup times that fit your schedule"
              },
              {
                icon: "💳",
                title: "Secure Payments",
                description: "Safe and secure digital payment options"
              }
            ].map((feature, index) => (
              <div key={index} className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-all duration-300 hover:-translate-y-1 border border-gray-100">
                <div className="text-4xl mb-4">{feature.icon}</div>
                <h3 className="text-lg font-semibold text-gray-900 mb-2">{feature.title}</h3>
                <p className="text-gray-600">{feature.description}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Testimonials */}
      <section className="py-20 bg-orange-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold text-gray-900 mb-4">What Students Say</h2>
            <p className="text-xl text-gray-600">Real feedback from college students</p>
          </div>
          <div className="grid md:grid-cols-3 gap-8">
            {[
              {
                quote: "SpeedyServe saved me so much time! No more waiting in long lunch queues.",
                name: "Priya Sharma",
                college: "Delhi University"
              },
              {
                quote: "Love the time slot feature. I can pre-order and pick up between classes perfectly.",
                name: "Arjun Patel",
                college: "IIT Bombay"
              },
              {
                quote: "The real-time notifications are amazing. I know exactly when my food is ready!",
                name: "Sneha Reddy",
                college: "Anna University"
              }
            ].map((testimonial, index) => (
              <div key={index} className="bg-white rounded-xl p-6 shadow-lg hover:shadow-xl transition-all duration-300">
                <div className="text-orange-500 text-2xl mb-4">"</div>
                <p className="text-gray-700 mb-4 italic">{testimonial.quote}</p>
                <div className="border-t pt-4">
                  <p className="font-semibold text-gray-900">{testimonial.name}</p>
                  <p className="text-gray-500 text-sm">{testimonial.college}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Canteen Dashboard */}
      <section id="canteen" className="py-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid lg:grid-cols-2 gap-12 items-center">
            <div className="space-y-8">
              <div>
                <h2 className="text-4xl font-bold text-gray-900 mb-4">For Canteen Owners</h2>
                <p className="text-xl text-gray-600">Streamline your operations with our smart dashboard</p>
              </div>
              <div className="space-y-4">
                {[
                  "Manage orders with slot-based scheduling",
                  "Reduce rush & improve efficiency",
                  "Track sales and dish performance",
                  "Seamless digital order management"
                ].map((feature, index) => (
                  <div key={index} className="flex items-center space-x-3">
                    <div className="w-6 h-6 bg-orange-500 rounded-full flex items-center justify-center">
                      <span className="text-white text-sm">✓</span>
                    </div>
                    <span className="text-gray-700">{feature}</span>
                  </div>
                ))}
              </div>
            </div>
            <div className="bg-gray-900 rounded-2xl p-6 shadow-2xl">
              <div className="bg-white rounded-lg p-4 space-y-4">
                <div className="flex justify-between items-center border-b pb-2">
                  <h3 className="font-semibold">Live Orders</h3>
                  <span className="bg-green-100 text-green-800 px-2 py-1 rounded-full text-xs">12 Active</span>
                </div>
                <div className="space-y-3">
                  {["Burger Combo - 12:30 PM", "Pizza Slice - 12:45 PM", "Sandwich - 1:00 PM"].map((order, index) => (
                    <div key={index} className="flex justify-between items-center p-2 bg-gray-50 rounded-lg">
                      <span className="text-sm">{order}</span>
                      <span className="text-orange-500 text-xs">Ready</span>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Download App */}
      <section id="download" className="py-20 bg-gradient-to-r from-orange-500 to-orange-600">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 text-center">
          <div className="text-white space-y-8">
            <h2 className="text-4xl font-bold">Download SpeedyServe Today</h2>
            <p className="text-xl opacity-90">Available on iOS and Android</p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <button className="bg-white text-gray-900 px-8 py-4 rounded-xl font-semibold hover:bg-gray-100 transition-all duration-300 hover:scale-105 flex items-center space-x-3">
                <PlayIcon className="w-6 h-6" />
                <span>Google Play</span>
              </button>
              <button className="bg-white text-gray-900 px-8 py-4 rounded-xl font-semibold hover:bg-gray-100 transition-all duration-300 hover:scale-105 flex items-center space-x-3">
                <span>🍎</span>
                <span>App Store</span>
              </button>
            </div>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-900 text-white py-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid md:grid-cols-4 gap-8">
            <div className="space-y-4">
              <div className="flex items-center space-x-3">
                <div className="bg-orange-500 rounded-lg p-2">
                  <span className="text-white font-bold">SS</span>
                </div>
                <span className="text-xl font-bold">SpeedyServe</span>
              </div>
              <p className="text-gray-400">Making college dining smarter and faster.</p>
            </div>
            <div className="space-y-4">
              <h4 className="font-semibold">Company</h4>
              <div className="space-y-2 text-gray-400">
                <a href="#" className="block hover:text-orange-500 transition-colors">About</a>
                <a href="#" className="block hover:text-orange-500 transition-colors">Contact</a>
                <a href="#" className="block hover:text-orange-500 transition-colors">Careers</a>
              </div>
            </div>
            <div className="space-y-4">
              <h4 className="font-semibold">Legal</h4>
              <div className="space-y-2 text-gray-400">
                <a href="#" className="block hover:text-orange-500 transition-colors">Privacy Policy</a>
                <a href="#" className="block hover:text-orange-500 transition-colors">Terms of Service</a>
                <a href="#" className="block hover:text-orange-500 transition-colors">Cookie Policy</a>
              </div>
            </div>
            <div className="space-y-4">
              <h4 className="font-semibold">Support</h4>
              <div className="space-y-2 text-gray-400">
                <a href="#" className="block hover:text-orange-500 transition-colors">Help Center</a>
                <a href="#" className="block hover:text-orange-500 transition-colors">FAQ</a>
                <a href="#" className="block hover:text-orange-500 transition-colors">Contact Support</a>
              </div>
            </div>
          </div>
          <div className="border-t border-gray-800 mt-8 pt-8 text-center text-gray-400">
            <p>&copy; 2025 SpeedyServe. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default SpeedyServeLanding;
