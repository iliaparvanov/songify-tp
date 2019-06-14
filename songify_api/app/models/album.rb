class Album < ApplicationRecord
  belongs_to :artist

  validates_presence_of :title
end
