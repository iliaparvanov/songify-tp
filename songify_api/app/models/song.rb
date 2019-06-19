class Song < ApplicationRecord
  belongs_to :album, optional: true
  has_many :artists
  belongs_to :user, optional: true

  validates_presence_of :title, :length
end
